    -- User creations
    
    CREATE USER postgres WITH ENCRYPTED PASSWORD '1234';
    
    -- Database creations
    
    CREATE DATABASE Electromechanics_shop OWNER postgres ENCODING 'UTF8';
    
    -- Connect to Electromechanics_ shop db to create data for its 'public' schema
    
    \c electromechanics_shop
    
    
     -- create new data types

CREATE TYPE OrderStatus AS ENUM (

    'Open',
    'Payment accepted',
    'Shipped',
    'Delivered'
    );

CREATE TYPE TicketStatus AS ENUM (

    'Open',
    'Processing',
    'Closed',
    'Return'
    );


CREATE TYPE PaymentMethodOnline AS ENUM (

    'Credit Card',
    'Google Pay',
    'Apple Pay'
    );

-- Customer

create table customer
(
    id           serial primary key,
    name         varchar(50)  not null,
    surname      varchar(50)  not null,
    fiscal_code  varchar(16)  not null,
    address      varchar(50),
    email        VARCHAR(254) not null,
    phone_number varchar(20),
    username     varchar(50)  not null unique ,
    password     bytea not null
);

-- Role

CREATE TABLE Role (

                      Name VARCHAR(25) PRIMARY KEY,
                      Description TEXT
);

-- Employee

CREATE TABLE Employee (

                          username  varchar(50) not null primary key,
                          name      varchar(50) not null,
                          surname   varchar(50) not null,
                          role_name varchar(25) not null,
                          password  bytea not null,

                          FOREIGN KEY (Role_Name) REFERENCES Role(Name) ON UPDATE CASCADE
);



-- Product Category

CREATE TABLE Product_Category (

                                  Name VARCHAR(25)    PRIMARY KEY,
                                  Description         VARCHAR(150)
);

--Discount

CREATE TABLE Discount (

                          ID          SERIAL PRIMARY KEY,
                          Percentage  SMALLINT CHECK(Percentage>=0 AND Percentage<=100) NOT NULL,
                          Start_Date  DATE NOT NULL,
                          End_Date    DATE NOT NULL
);

-- Product

CREATE TABLE Product (

                         Product_Alias   VARCHAR(13) PRIMARY KEY,
                         Name            VARCHAR(50) NOT NULL,
                         Brand           VARCHAR(50) NOT NULL,
                         Description     TEXT,
                         Purchase_Price  NUMERIC(10, 2) CHECK(Purchase_Price >= 0) NOT NULL,
                         Sale_Price      NUMERIC(10, 2) CHECK(Sale_Price >= 0) NOT NULL,
                         Quantity        INTEGER CHECK(Quantity >= 0) NOT NULL,
                         Category_Name   VARCHAR(25) NOT NULL,
                         evidence        boolean default false not null,

                         FOREIGN KEY (Category_Name) REFERENCES Product_Category(Name) ON UPDATE CASCADE
);

--Owns

CREATE TABLE Owns (

                      ID_Discount     INTEGER NOT NULL,
                      Product_Alias   VARCHAR(13) NOT NULL,

                      PRIMARY KEY (ID_Discount, Product_Alias),
                      FOREIGN KEY (ID_Discount) REFERENCES Discount(ID) ON UPDATE CASCADE,
                      FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE
);

-- Assistance ticket

CREATE TABLE Assistance_Ticket(

                                  ID              SERIAL PRIMARY KEY,
                                  Description     TEXT NOT NULL,
                                  ID_Customer     INTEGER NOT NULL,
                                  Product_Alias   VARCHAR(13) NOT NULL,

                                  FOREIGN KEY (ID_Customer) REFERENCES Customer(ID) ON UPDATE CASCADE,
                                  FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE
);

--Ticket Status

CREATE TABLE Ticket_Status(

                              ID SERIAL   PRIMARY KEY,
                              Status      TicketStatus NOT NULL,
                              Description TEXT,
                              TS_Date     TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
                              ID_Ticket   INTEGER NOT NULL,

                              FOREIGN KEY (ID_Ticket) REFERENCES Assistance_Ticket(ID) ON UPDATE CASCADE
);

--Online Order

CREATE TABLE Online_Order(

                             ID SERIAL   PRIMARY KEY,
                             OO_DateTime TIMESTAMP NOT NULL DEFAULT localtimestamp,
                             ID_Customer INTEGER NOT NULL,

                             FOREIGN KEY (ID_Customer) REFERENCES Customer(ID) ON UPDATE CASCADE
);

--Online Invoice

CREATE TABLE Online_Invoice(

                               ID              SERIAL PRIMARY KEY,
                               ID_Order        INTEGER NOT NULL,
                               Transaction_ID  VARCHAR(30) NOT NULL,
                               Payment_Type    PaymentMethodOnline NOT NULL,
                               OI_Date         DATE NOT NULL DEFAULT CURRENT_DATE,
                               Total_Price     NUMERIC(10, 2) CHECK(Total_Price >= 0) NOT NULL,

                               FOREIGN KEY (ID_Order) REFERENCES Online_Order(ID) ON UPDATE CASCADE
);


--Order Status

CREATE TABLE Order_Status(

                             ID SERIAL   PRIMARY KEY,
                             Status      OrderStatus NOT NULL,
                             Description TEXT,
                             OS_DateTime TIMESTAMP NOT NULL DEFAULT localtimestamp,
                             ID_Order    INTEGER NOT NULL,

                             FOREIGN KEY (ID_Order) REFERENCES Online_Order(ID) ON UPDATE CASCADE
);

--Contains

CREATE TABLE Contains(

                         ID_Order        INTEGER NOT NULL,
                         Product_Alias   VARCHAR(13) NOT NULL,
                         Quantity        INTEGER CHECK(Quantity > 0) NOT NULL,
                         Price_Applied   NUMERIC(10, 2) CHECK(Price_applied >= 0) NOT NULL,

                         PRIMARY KEY (ID_Order, Product_Alias),
                         FOREIGN KEY (ID_Order) REFERENCES Online_Order(ID) ON UPDATE CASCADE,
                         FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE
);



--media

create table media
(
    id       serial  primary key,
    mimetype varchar(100),
    filename varchar(100),
    media    bytea,
    thumb    bytea
);


--rappresented_by

create table rappresented_by
(
    product_alias varchar(13) not null,
    id_media      integer     not null,

    primary key (product_alias, id_media),
    FOREIGN key  (product_alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE,
    FOREIGN key (id_media) references media(id) ON UPDATE CASCADE
);


--Trigger to update product quantity after an online invoice has been created

CREATE OR REPLACE FUNCTION update_quantity() RETURNS trigger AS $update_quantity$
BEGIN
    UPDATE product
    SET quantity = P_New_quantity.New_Quantity
    FROM (
             SELECT P_sold.product_Alias
                  , (P.Quantity - P_sold.Quantity) AS New_Quantity
             FROM Product AS P
                      INNER JOIN (
                 SELECT C.product_alias
                      , C.quantity
                 FROM online_invoice AS OI
                          INNER JOIN online_order AS OO on OO.id = OI.id_order
                          INNER JOIN contains AS C on OO.id = C.id_order
                 WHERE OI.id = NEW.ID
             ) AS P_sold
                                 ON P.product_Alias = P_sold.product_Alias
         ) AS P_New_quantity
    WHERE product.product_alias = P_New_quantity.product_alias;
    RETURN NULL;
END;
$update_quantity$ LANGUAGE plpgsql;

CREATE TRIGGER update_quantity
    AFTER INSERT ON online_invoice
    FOR EACH ROW EXECUTE FUNCTION update_quantity();



--POPULATION OF DB

    --Customer
    
    INSERT INTO Customer (Name , Surname , Fiscal_Code , Address , Email , Phone_Number , Username , Password)
        VALUES
            ('Didem' , 'Kucukkaya' , 'KCKDDM95R62Z126K' , 'Via Malta 13' , 'kucukkayadidem@gmail.com' , '3516387612' , 'didemk' , sha384('1234')),
            ('David' , 'Tozzi' , 'TZZDVD89T53P148T' , 'Corso Vittorio Emanuele 2' , 'david.tozzi@gmail.com' , '3248302589' , 'dtozzi' , sha384('1234')),
            ('Vincenzo' , 'Capitani' , 'CPTVNC75C46O521V' , 'Corso Del Popolo 4' , 'capitani-vince@gmail.com' , '1125049841' , 'vincecapi' , sha384('1234')),
            ('Gainpetro' , 'Nicoletti' , 'KCKDDM95R62Z122L' , 'Via Belzoni 06' , 'Nicoletti@gmail.com' , '3516387772' , 'Nicoletti' , sha384('1234')),
            ('Stefano' , 'Alberton' , 'QWEDVD89T53P785T' , 'Via Belzoni 04' , 'Alberton@gmail.com' , '3248345789' , 'Alberton' , sha384('1234')),
            ('Massimiliano' , 'Belluomini' , 'CPTVNC75C485221Z' , 'Corso Del Popolo 4' , 'Belluomini@gmail.com' , '1156478941' , 'Belluomini' , sha384('1234')),
            ('Chiarello' , 'Federico' , 'KCKDDM95R62T175K' , 'Via Malta 05' , 'Federico@gmail.com' , '3516489312' , 'Federico' , sha384('1234')),
            ('Giurisato' , 'Francesco' , 'TZZDVD89T53P333Q' , 'Corso Vittorio Emanuele 2' , 'Francesco@gmail.com' , '7895242589' , 'Francesco' , sha384('1234')),
            ('Mahmoud' , 'Wahba' , 'QWSADF75C46O521K' , 'Via Belzoni 4' , 'Wahba-Mahmoud@gmail.com' , '1127854121' , 'Wahba' , sha384('1234')),
            ('Ali' , 'Wahba' , 'QWSADF75C785416M' , 'Via Belzoni 4' , 'Wahba-Ali@gmail.com' , '3541854122' , 'Ali' , sha384('1234')),
            ('Monica' , 'Fracasso' , 'FRCMNC99A32M536L' , 'Via Beato Luca Belludi 9' , 'monicafracasso@gmail.com' , '5365124088' , 'monifra' , sha384('1234')),
            ('Martina' , 'Bellizzi' , 'BLZMRT21C57H329P' , 'Via Giordano Bruno 7' , 'bellmartina@gmail.com' , '6387864300' , 'bellimarti' , sha384('1234')),
            ('Alberto' , 'Geniola' , 'GNLBRT02F25Z123A' , 'Via Domenico Vandelli 27' , 'albertogen@gmail.com' , '2534873310' , 'genalberto' , sha384('1234')),
            ('Flippo' , 'Novello' , 'NVLFLP90T98O694L' , 'Via Cappelli 16' , 'novello.flippo@gmail.com' , '6147389924' , 'flippono' , sha384('1234'));

    --Role
    
    INSERT INTO Role (Name , Description)
        VALUES 
            ('Technician' , 'Dealing with the problems of defective products.'), 
            ('Seller' , 'Informs the customer about the features of the products and direct the customers to the product they need.'), 
            ('Administrator' , 'Admin of the software who has all authorization and authentication.'), 
            ('Accountant' , 'Manages everything related to the company''s income and expenses.'), 
            ('Warehouse Manager' , 'Deals with the stock numbers and deliveries of the products in the warehouse.');

    --Employee
    
    INSERT INTO Employee (Username , Password , Name , Surname , Role_Name)
        VALUES 
            ('Ege' , sha384('6879') , 'Ege' , 'Turkyener' , 'Technician'), 
            ('Margherita' , sha384('6879') , 'Margherita' , 'Bud' , 'Seller'), 
            ('Roberto' , sha384('6879') , 'Roberto' , 'Grillo' , 'Administrator'), 
            ('Nur' , sha384('6879') , 'Nur' , 'Guerzoni' , 'Accountant'), 
            ('Gaia' , sha384('6879') , 'Gaia' , 'Rosati' , 'Warehouse Manager'), 
            ('Tommaso' , sha384('6879') , 'Tommaso' , 'Guarnieri' , 'Technician'), 
            ('Andrea' , sha384('6879') , 'Andrea' , 'Fabrello' , 'Seller'), 
            ('Boris' , sha384('6879') , 'Boris' , 'Drago' , 'Warehouse Manager'), 
            ('Ticone' , sha384('6879') , 'Ticone' , 'Scatto' , 'Technician'), 
            ('Virginio' , sha384('6879') , 'Virginio' , 'Moriconi' , 'Seller');
    
    --Product_Category
    
    INSERT INTO Product_Category (Name, Description)
        VALUES 
            ('Drill', '6465661284454 150kVA'), 
            ('Screwdriver', 'Electrical Screwdriver with multiple tools'), 
            ('Compressors', 'Air compressors'), 
            ('Generator', 'Generator for large systems'), 
            ('Transformer', 'Transformer'), 
            ('Pump', 'Pump'), 
            ('Electrical Motor', 'Motor'), 
            ('Ampermeter', 'Ampermeter'), 
            ('Multimeter', 'Multimeter'), 
            ('Cable', 'Cable');
    
    --Online Order
    
    INSERT INTO Online_Order (ID_Customer) 
        VALUES
            (2), 
            (1), 
            (3), 
            (4), 
            (5), 
            (6), 
            (7), 
            (8), 
            (9), 
            (10);
    
    --Online Invoice
    
    INSERT INTO Online_Invoice (ID_Order, Transaction_ID, Payment_Type, Total_Price) 
        VALUES
            (1, 5655, 'Credit Card', 2070), 
            (3, 785, 'Google Pay', 210 ), 
            (2, 7892, 'Apple Pay', 180 ), 
            (4, 895, 'Credit Card', 1090), 
            (5, 8520, 'Google Pay', 110 ), 
            (6, 4872, 'Apple Pay', 1350 ), 
            (7, 7792, 'Apple Pay', 75 );
    
    --Order Status
    
    INSERT INTO Order_Status (Status, Description, ID_Order) 
        VALUES
            ('Shipped' , 'On its way' , 3), 
            ('Payment accepted' , 'Processing' , 2), 
            ('Open' , 'Waiting for the payment to be accepted' , 1), 
            ('Shipped' , 'On its way' , 4), 
            ('Payment accepted' , 'Processing' , 5), 
            ('Open' , 'Waiting for the payment to be accepted' , 6), 
            ('Open' , 'Waiting for the payment to be accepted' , 7), 
            ('Shipped' , 'On its way' , 8), 
            ('Payment accepted' , 'Processing' , 9), 
            ('Shipped' , 'On its way' , 10);
    
    
 
    --Product
    
    INSERT INTO Product (Product_Alias, Name, Brand, Description, Quantity, Purchase_Price, Sale_Price, Category_Name)
        VALUES 
            ('6465661284454', 'Drill', 'DeWALT', 'Suitable for different materials: For drilling and screwdriving in wood and metal or impact drilling in masonry', '20', '150', '200', 'Drill'), 
            ('6465661284458', 'Screwdriver', 'BOSCH', 'Micro - USB charging system with reduced charging time of 3 hours', '12', '45', '70', 'Screwdriver'), 
            ('6465661284473', 'Air compressor', 'Automan', 'Compressor with aluminum pistons. 24L tank', '10', '65', '109', 'Compressors'), 
            ('6465661284410', 'Generator', 'GEN100', '200 KVA generator.', '5', '100', '110', 'Generator'), 
            ('6465661284411', 'Transformer', 'TRAUTO', 'Fully redundant transformer 150KVA', '0', '200', '350', 'Transformer'), 
            ('6465661284412', 'Pump', 'PMOTO', 'Hydra pump for large systems.', '0', '15', '25','Pump'), 
            ('6465661284413', 'Electrical Motor', 'General Electric', 'Electrical motor with the capacity 15 kW.', '15', '50', '150','Electrical Motor'), 
            ('6465661284414', 'Ampermeter', 'Scheneider', 'Perfect ampermeter for 4 years gurantee.', '100', '5', '10','Ampermeter'), 
            ('6465661284415', 'Multimeter', 'Siemens', 'Multimeter with many functionalities.', '100', '10', '15','Multimeter'), 
            ('6465661284416', 'Copper Cable', 'Scheneider', '100m copper cable.', '10', '10', '15','Cable');
    

          
    --Contains
    
    INSERT INTO Contains(ID_Order, Product_Alias, Quantity, Price_applied)
        VALUES 
            (1, '6465661284454', '10', '2000'), 
            (1, '6465661284458', '1', '70'), 
            (2, '6465661284458', '1', '70'), 
            (3, '6465661284410', '1', '110'), 
            (3, '6465661284458', '3', '210'), 
            (4, '6465661284473', '10', '1090'), 
            (5, '6465661284410', '1', '110'), 
            (6, '6465661284411', '3', '1350'), 
            (7, '6465661284412', '3', '75'), 
            (8, '6465661284413', '10', '1500'), 
            (9, '6465661284413', '1', '150'), 
            (10, '6465661284413', '3', '450');
    
    --Discount
    
    INSERT INTO Discount(Percentage, Start_Date, End_Date)
        VALUES 
            (20, '10-10-2020 00:00:00', '20-10-2020 00:00:00'), 
            (15, '08-12-2021 00:00:00', '31-12-2021 00:00:00'), 
            (10, '18-11-2022 00:00:00', '25-11-2022 00:00:00');
    
    --Owns
    
    INSERT INTO Owns(ID_Discount, Product_Alias)
        VALUES 
            (1, '6465661284454'), 
            (2, '6465661284458'), 
            (3, '6465661284412'), 
            (1, '6465661284415'), 
            (3, '6465661284416');
    

    --Assistance Ticket
    
    INSERT INTO Assistance_Ticket (Description ,ID_Customer , Product_Alias)
        VALUES
            ('Before the guarantee period expires, product repair is requested.' ,1 , '6465661284454'), 
            ('Return of the product is requested before the guarantee period expires.' ,2, '6465661284458'), 
            ('The product was replaced with a new one.' ,3 , '6465661284458'), 
            ('The product has been repaired.' ,4 , '6465661284413'), 
            ('The product is faulty.' ,5 , '6465661284416'), 
            ('The product is requested to be replaced with another product.' ,6 , '6465661284410'), 
            ('The delivery date of the product is delayed.' ,7 , '6465661284414'), 
            ('The delivery date of the product is delayed.' ,8 , '6465661284414'), 
            ('The product is faulty.' ,9 , '6465661284415'), 
            ('Return of the product is requested before the guarantee period expires.' ,10 , '6465661284412');
    
    --Ticket Status
    
    INSERT INTO Ticket_Status (Status , Description  , ID_Ticket)
        VALUES 
            ('Open' , 'Before the guarantee period expires, product repair is requested.'  , 1), 
            ('Processing' , 'Return of the product is requested before the guarantee period expires.'  , 2), 
            ('Closed' , 'The product was replaced with a new one.'   , 3), 
            ('Closed' , 'The product has been repaired.'  , 4), 
            ('Return' , 'The product is faulty.'  , 5), 
            ('Open' , 'The product is requested to be replaced with another product.'  , 6), 
            ('Open' , 'The delivery date of the product is delayed.'  , 7), 
            ('Open' , 'The delivery date of the product is delayed.'  , 8), 
            ('Return' , 'The product is faulty.' , 9), 
            ('Processing' , 'Return of the product is requested before the guarantee period expires.' , 10);
