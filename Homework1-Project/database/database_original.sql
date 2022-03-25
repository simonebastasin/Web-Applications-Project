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

CREATE TYPE PaymentMethod AS ENUM (

    'Cash',
    'Credit Card',
    'Google Pay',
    'Apple Pay'
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
    username     varchar(50)  not null,
    password     bytea
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
                          password  bytea,

                          FOREIGN KEY (Role_Name) REFERENCES Role(Name) ON UPDATE CASCADE
);



-- Supplier

CREATE TABLE Supplier (

                          id           serial primary key,
                          name         varchar(50)  not null,
                          address      varchar(50),
                          phone_number varchar(20),
                          email        VARCHAR(254) not null,
                          iban         varchar(30),
                          vat_number   varchar(30)
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
                         Picture         BYTEA,
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
                                  E_Username      VARCHAR(50),
                                  ID_Customer     INTEGER NOT NULL,
                                  Product_Alias   VARCHAR(13) NOT NULL,

                                  FOREIGN KEY (E_Username) REFERENCES Employee(Username) ON UPDATE CASCADE,
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

--Supplier Invoice

CREATE TABLE Supplier_Invoice(

                                 ID          SERIAL PRIMARY KEY,
                                 SP_Date     DATE NOT NULL,
                                 Total_Price NUMERIC(10, 2) CHECK(Total_Price >= 0) NOT NULL
);

-- Restock

CREATE TABLE Restock(

                        ID_Invoice      INTEGER NOT NULL,
                        Product_Alias   VARCHAR(13) NOT NULL,
                        E_Username      VARCHAR(50) NOT NULL,
                        Supplier_ID     INTEGER NOT NULL,
                        Quantity        INTEGER CHECK(Quantity > 0) NOT NULL,
                        Arrived         BOOLEAN DEFAULT false,

                        PRIMARY KEY (ID_Invoice, Product_Alias, E_Username, Supplier_ID),
                        FOREIGN KEY (ID_Invoice) REFERENCES Supplier_Invoice(ID) ON UPDATE CASCADE,
                        FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE,
                        FOREIGN KEY (E_Username) REFERENCES Employee(Username) ON UPDATE CASCADE,
                        FOREIGN KEY (Supplier_ID) REFERENCES Supplier(ID) ON UPDATE CASCADE
);

--Physical Receipt

CREATE TABLE Physical_Receipt(

                                 ID                          SERIAL PRIMARY KEY,
                                 Transaction_ID              VARCHAR(30) NOT NULL,
                                 Payment_Type PaymentMethod  NOT NULL,
                                 PR_DateTime                 TIMESTAMP NOT NULL DEFAULT localtimestamp,
                                 Total_Price                 NUMERIC(10, 2) CHECK(Total_Price >= 0) NOT NULL
);

--Physical Invoice

CREATE TABLE Physical_Invoice(

                                 ID                  SERIAL PRIMARY KEY,
                                 Transaction_ID      VARCHAR(30)  NOT NULL,
                                 Payment_Type        PaymentMethod  NOT NULL,
                                 PI_Date             DATE NOT NULL DEFAULT CURRENT_DATE,
                                 Total_Price         NUMERIC(10, 2) CHECK(Total_Price >= 0) NOT NULL,
                                 Business_Name       VARCHAR(50),
                                 Name                VARCHAR(50) NOT NULL,
                                 Surname             VARCHAR(50) NOT NULL,
                                 VAT_or_Fiscal_Code  VARCHAR(16) NOT NULL,
                                 Address             VARCHAR(50) NOT NULL
);

--Sell 1

CREATE TABLE Sell_1(

                       ID_Receipt          SERIAL NOT NULL,
                       Product_Alias       VARCHAR(13) NOT NULL,
                       Quantity            INTEGER CHECK(Quantity > 0) NOT NULL,
                       Price_Applied       NUMERIC(10, 2) CHECK(Price_applied >= 0) NOT NULL,

                       PRIMARY KEY (ID_Receipt, Product_Alias),
                       FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE,
                       FOREIGN KEY (ID_Receipt) REFERENCES Physical_Receipt(ID) ON UPDATE CASCADE
);

--Sell 2

CREATE TABLE Sell_2(

                       ID_Phys_Invoice     SERIAL NOT NULL,
                       Product_Alias       VARCHAR(13) NOT NULL,
                       Quantity            INTEGER CHECK(Quantity > 0) NOT NULL,
                       Price_Applied       NUMERIC(10, 2) CHECK(Price_applied >= 0) NOT NULL,

                       PRIMARY KEY (ID_Phys_Invoice, Product_Alias),
                       FOREIGN KEY (Product_Alias) REFERENCES Product(Product_Alias) ON UPDATE CASCADE,
                       FOREIGN KEY (ID_Phys_Invoice) REFERENCES Physical_Invoice(ID) ON UPDATE CASCADE
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

--Trigger to update product quantity after a physical receipt has been created

CREATE OR REPLACE FUNCTION update_quantity_phys_receipt() RETURNS trigger AS $update_quantity_phys_receipt$
BEGIN
    UPDATE product
    SET quantity = P_New_quantity.New_Quantity
    FROM (
             SELECT P_sold.product_Alias
                  , P.Quantity - P_sold.Quantity as New_Quantity
             FROM Product AS P
                      INNER JOIN (
                 SELECT S.product_alias
                      , S.quantity
                 FROM physical_receipt AS PR
                          INNER JOIN sell_1 AS S ON PR.id = S.id_receipt
                 WHERE PR.id = NEW.ID
             ) AS P_sold
                                 ON P.product_Alias = P_sold.product_Alias
         ) AS P_New_quantity
    WHERE product.product_alias = P_New_quantity.product_alias;
    RETURN NULL;
END;
$update_quantity_phys_receipt$ LANGUAGE plpgsql;

CREATE TRIGGER update_quantity_phys_receipt
    AFTER INSERT ON physical_receipt
    FOR EACH ROW EXECUTE FUNCTION update_quantity_phys_receipt();

--Trigger to update product quantity after a physical invoice has been created

CREATE OR REPLACE FUNCTION update_quantity_phys_invoice() RETURNS trigger AS $update_quantity_phys_invoice$
BEGIN
    UPDATE product
    SET quantity = P_New_quantity.New_Quantity
    FROM (
             SELECT P_sold.product_Alias
                  , P.Quantity - P_sold.Quantity as New_Quantity
             FROM Product AS P
                      INNER JOIN (
                 SELECT S.product_alias
                      , S.quantity
                 FROM physical_invoice AS PI
                          INNER JOIN sell_2 AS S ON PI.id = S.id_phys_invoice
                 WHERE PI.id = NEW.ID
             ) AS P_sold
                                 ON P.product_Alias = P_sold.product_Alias
         ) AS P_New_quantity
    WHERE product.product_alias = P_New_quantity.product_alias;
    RETURN NULL;
END;
$update_quantity_phys_invoice$ LANGUAGE plpgsql;

CREATE TRIGGER update_quantity_phys_invoice
    AFTER INSERT ON physical_invoice
    FOR EACH ROW EXECUTE FUNCTION update_quantity_phys_invoice();




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
    
    --Physical Receipt
    
    INSERT INTO Physical_Receipt (Transaction_ID, Payment_Type, Total_Price) 
        VALUES
            (852 , 'Credit Card', 200), 	
            (860 , 'Cash' , 140), 
            (125 , 'Google Pay' , 330), 
            (812 , 'Credit Card', 1400), 	
            (820 , 'Cash' , 750), 
            (195 , 'Google Pay' , 200), 
            (841 , 'Credit Card', 610), 	
            (123 , 'Cash' , 60), 
            (726 , 'Google Pay' , 1250), 
            (931 , 'Google Pay' , 400);
    
    --Physical Invoice
    
    INSERT INTO Physical_Invoice (Transaction_ID, Payment_Type, Total_Price, Business_Name, Name, Surname, VAT_or_Fiscal_Code, Address) 
        VALUES
            (785, 'Credit Card', 200, 'DEI', 'Alberto', 'Rossi', 'RSSLRT98T08G224E', 'Via Gradenigo 6' ), 
            (790, 'Cash', 340, 'UNIPD', 'Gianpietro', 'Verdi', 'VRSLRT98T08G224E', 'Via Gradenigo 7' ), 
            (123, 'Google Pay', 495, 'Shoppy15', 'Alberto', 'Romano', 'RMKLRT98T08G224E', 'Via Gradenigo 11' ), 
            (456, 'Credit Card', 60, 'DEI', 'Gianpietro', 'Rossi', 'RSSLRT98T08G224E', 'Via Gradenigo 6' ), 
            (789, 'Cash', 750, 'UNIPD', 'Gianpietro', 'Verdi', 'VRSLRT98T08G224E', 'Via Gradenigo 78' ), 
            (852, 'Google Pay', 70, 'Shoppy15', 'Francesco', 'Romano', 'RMNLRT98T08G224E', 'Via Gradenigo 135' ), 
            (965, 'Credit Card', 700, 'DEI', 'Alberto', 'Rossi', 'RSSLRT98T08G224E', 'Via Gradenigo 6' ), 
            (158, 'Cash', 210, 'UNIPD', 'Gianpietro', 'Verdi', 'VRDLRT98T08G224E', 'Via Gradenigo 7' ), 
            (685, 'Google Pay', 100, 'Shoppy11', 'Ege', 'Romano', 'RMNLRT98T08G224E', 'Via Gradenigo 13' ), 
            (439, 'Google Pay', 350, 'Shoppy15', 'Alberto', 'Romano', 'RMNLRT98T08G224E', 'Via Gradenigo 11' );
    
    --Product
    
    INSERT INTO Product (Product_Alias, Name, Brand, Description, Quantity, Purchase_Price, Sale_Price, Picture, Category_Name)
        VALUES 
            ('6465661284454', 'Drill', 'DeWALT', 'Suitable for different materials: For drilling and screwdriving in wood and metal or impact drilling in masonry', '20', '150', '200', 'xDEADBEEC', 'Drill'), 
            ('6465661284458', 'Screwdriver', 'BOSCH', 'Micro - USB charging system with reduced charging time of 3 hours', '12', '45', '70', 'xDEADBEEF', 'Screwdriver'), 
            ('6465661284473', 'Air compressor', 'Automan', 'Compressor with aluminum pistons. 24L tank', '10', '65', '109', 'xDEADSEFF', 'Compressors'), 
            ('6465661284410', 'Generator', 'GEN100', '200 KVA generator.', '5', '100', '110', 'xDEADBSSF', 'Generator'), 
            ('6465661284411', 'Transformer', 'TRAUTO', 'Fully redundant transformer 150KVA', '0', '200', '350', 'xDEDFBEFF', 'Transformer'), 
            ('6465661284412', 'Pump', 'PMOTO', 'Hydra pump for large systems.', '0', '15', '25', 'xDEASSEFF', 'Pump'), 
            ('6465661284413', 'Electrical Motor', 'General Electric', 'Electrical motor with the capacity 15 kW.', '15', '50', '150', 'xDEASSSFF', 'Electrical Motor'), 
            ('6465661284414', 'Ampermeter', 'Scheneider', 'Perfect ampermeter for 4 years gurantee.', '100', '5', '10', 'xDAADSSEFF', 'Ampermeter'), 
            ('6465661284415', 'Multimeter', 'Siemens', 'Multimeter with many functionalities.', '100', '10', '15', 'xDEASDFF', 'Multimeter'), 
            ('6465661284416', 'Copper Cable', 'Scheneider', '100m copper cable.', '10', '10', '15', 'xDEAVVVFF', 'Cable');
    
    --Supplier
    
    INSERT INTO Supplier(Name, Address, Phone_Number, Email, IBAN, VAT_Number)
        VALUES 
            ('Shop1', 'Piazza Duomo 11', '33432654', 'shop1@shop.com', 'IT12312312312312312355553', '25459521'), 
            ('Shop2', 'Piazza Boo 15', '334325114', 'shop2@shop.com', 'IT12312312312312312332233', '98859521'), 
            ('Shop3', 'Piazza Duomo 12', '33432655', 'shop3@shop.com', 'IT15831231231231231235587', '25459529'), 
            ('Shop4', 'Piazza Boo 17', '334325116', 'shop4@shop.com', 'IT12312312312312312332255', '98859558');
        
    --Supplier Invoice
    
    INSERT INTO Supplier_Invoice (SP_Date, Total_Price) 
        VALUES
            ('15-02-2021', 1500), 
            ('25-05-2021', 135), 
            ('11-02-2021', 650), 
            ('22-05-2021', 230), 
            ('13-02-2021', 500), 
            ('24-05-2021', 20), 
            ('18-02-2021', 100), 
            ('29-05-2021', 250), 
            ('13-02-2021', 500), 
            ('22-05-2021', 230);
        
    --Restock
    
    INSERT INTO Restock(ID_Invoice, Product_Alias, E_Username, Supplier_ID, Quantity, Arrived)
        VALUES 
            (1, '6465661284454', 'Ege', '2', '10', false), 
            (2, '6465661284458', 'Gaia', '1', '3', false), 
            (3, '6465661284473', 'Tommaso', '3', '10', false), 
            (4, '6465661284416', 'Ege', '1', '23', false), 
            (5, '6465661284413', 'Andrea', '2', '10', true), 
            (6, '6465661284414', 'Ege', '3', '4', true), 
            (7, '6465661284415', 'Nur', '2', '10', false), 
            (8, '6465661284413', 'Ege', '4', '5', true), 
            (9, '6465661284413', 'Boris', '2', '10', false), 
            (10, '6465661284416', 'Boris', '4', '23', false);

          
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
    
    --Sell_1
    
    INSERT INTO Sell_1(ID_Receipt, Product_Alias, Quantity, Price_Applied)
        VALUES 
            (1, '6465661284454', '1', '160'), 
            (2, '6465661284458', '2', '140'), 
            (3, '6465661284410', '3', '330'), 
            (4, '6465661284411', '4', '1400'), 
            (5, '6465661284413', '5', '750'), 
            (6, '6465661284454', '1', '200'), 
            (7, '6465661284454', '2', '400'), 
            (7, '6465661284458', '3', '210'), 
            (8, '6465661284416', '4', '60'), 
            (9, '6465661284412', '5', '125'), 
            (10, '6465661284411', '1', '350'), 
            (10, '6465661284412', '2', '50');
    
    --Sell_2
    
    INSERT INTO Sell_2(ID_Phys_Invoice, Product_Alias, Quantity, Price_Applied)
        VALUES 
            (1, '6465661284454', '1', '200'), 
            (2, '6465661284458', '2', '140'), 
            (2, '6465661284454', '1', '200'), 
            (3, '6465661284413', '3', '450'), 
            (3, '6465661284415', '3', '45'), 
            (4, '6465661284415', '4', '60'), 
            (5, '6465661284416', '5', '75'), 
            (6, '6465661284458', '1', '70'), 
            (7, '6465661284411', '2', '700'), 
            (8, '6465661284458', '3', '210'), 
            (9, '6465661284412', '4', '100'), 
            (10, '6465661284458', '5', '350');
    
    --Assistance Ticket
    
    INSERT INTO Assistance_Ticket (Description , E_Username , ID_Customer , Product_Alias)
        VALUES
            ('Before the guarantee period expires, product repair is requested.' , 'Ege' , 1 , '6465661284454'), 
            ('Return of the product is requested before the guarantee period expires.' , 'Margherita' , 2, '6465661284458'), 
            ('The product was replaced with a new one.' , 'Gaia' , 3 , '6465661284458'), 
            ('The product has been repaired.' , 'Tommaso' , 4 , '6465661284413'), 
            ('The product is faulty.' , 'Boris' , 5 , '6465661284416'), 
            ('The product is requested to be replaced with another product.' , 'Virginio' , 6 , '6465661284410'), 
            ('The delivery date of the product is delayed.' , 'Gaia' , 7 , '6465661284414'), 
            ('The delivery date of the product is delayed.' , 'Boris' , 8 , '6465661284414'), 
            ('The product is faulty.' , 'Ege' , 9 , '6465661284415'), 
            ('Return of the product is requested before the guarantee period expires.' , 'Andrea' , 10 , '6465661284412');
    
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
