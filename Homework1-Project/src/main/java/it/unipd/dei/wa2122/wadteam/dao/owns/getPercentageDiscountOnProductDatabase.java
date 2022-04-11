package it.unipd.dei.wa2122.wadteam.dao.owns;

public class getPercentageDiscountOnProductDatabase {
/*
    SELECT discount.percentage
    FROM Owns
    INNER JOIN Discount ON Owns.ID_Discount = Discount.id
    INNER JOIN Product ON Owns.Product_Alias = Product.Product_Alias
    WHERE End_Date >= CURRENT_DATE AND Start_Date <= CURRENT_DATE AND Product.Product_Alias = '6465661284416'

 */

}
