<%--
  Created by IntelliJ IDEA.
  User: matteolando
  Date: 12/04/2022
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<c:import url="/jsp/include/header.jsp"/>

<h2>Create new discount</h2>


<form method="POST" action="">

    <label for="percentage" >percentage:</label>
    <input type="number" id="percentage" name="percentage" min="1" max="100" value="1">

    <p class="startDate">Enter start Day:</p>
    <div class="startDate">
    <span>
      <label for="startDateday">Day:</label>
      <select id="startDateday" name="startDateday">
        <option selected>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
        <option>6</option>
        <option>7</option>
        <option>8</option>
        <option>9</option>
        <option>10</option>
        <option>11</option>
        <option>12</option>
        <option>13</option>
      	<option>14</option>
      	<option>15</option>
      	<option>16</option>
      	<option>17</option>
      	<option>18</option>
      	<option>19</option>
      	<option>20</option>
      	<option>21</option>
        <option>22</option>
        <option>23</option>
        <option>24</option>
        <option>25</option>
        <option>26</option>
        <option>27</option>
        <option>28</option>
        <option>29</option>
        <option>30</option>
        <option>31</option>
      </select>
    </span>
        <span>
      <label for="startDatemonth">Month:</label>
      <select id="startDatemonth" name="startDatemonth">
        <option selected value="1">January</option>
        <option value="2">February</option>
        <option value="3">March</option>
        <option value="4">April</option>
        <option value="5">May</option>
        <option value="6">June</option>
        <option value="7">July</option>
        <option value="8">August</option>
        <option value="9">September</option>
        <option value="10">October</option>
        <option value="11">November</option>
        <option value="12">December</option>
      </select>
    </span>
        <span>
      <label for="startDateyear">Year:</label>
      <select id="startDateyear" name="startDateyear">
      <option selected>2022</option>
        <option>2023</option>
        <option>2024</option>
        <option>2025</option>
        <option>2026</option>
      </select>
    </span>
    </div>


    <p class="fallbackLabel">Enter end Day:</p>
    <div class="fallbackDatePicker">
    <span>
      <label for="endDateday">Day:</label>
      <select id="endDateday" name="endDateday">
        <option selected>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
        <option>6</option>
        <option>7</option>
        <option>8</option>
        <option>9</option>
        <option>10</option>
        <option>11</option>
        <option>12</option>
        <option>13</option>
      	<option>14</option>
      	<option>15</option>
      	<option>16</option>
      	<option>17</option>
      	<option>18</option>
      	<option>19</option>
      	<option>20</option>
      	<option>21</option>
        <option>22</option>
        <option>23</option>
        <option>24</option>
        <option>25</option>
        <option>26</option>
        <option>27</option>
        <option>28</option>
        <option>29</option>
        <option>30</option>
        <option>31</option>
      </select>
    </span>
        <span>
      <label for="endDatemonth">Month:</label>
      <select id="endDatemonth" name="endDatemonth">
        <option selected value="1">January</option>
        <option value="2">February</option>
        <option value="3">March</option>
        <option value="4">April</option>
        <option value="5">May</option>
        <option value="6">June</option>
        <option value="7">July</option>
        <option value="8">August</option>
        <option value="9">September</option>
        <option value="10">October</option>
        <option value="11">November</option>
        <option value="12">December</option>
      </select>
    </span>
        <span>
      <label for="endDateyear">Year:</label>
      <select id="endDateyear" name="endDateyear">
      <option selected>2022</option>
        <option>2023</option>
        <option>2024</option>
        <option>2025</option>
        <option>2026</option>
      </select>
    </span>
    </div>

    <p>Select the products to be discounted</p>
    <c:forEach var="prod" items="${productList}">
        <input type="checkbox" name="productList" value="${prod.alias}">
        ${prod.alias}&nbsp
        <a href="<c:url value="/productDetail/${prod.alias}"/>">${prod.name}</a>&nbsp
        ${prod.brand}&nbsp
        ${prod.sale}&nbsp
        ${prod.quantity}&nbsp
        ${prod.category}&nbsp
        ${prod.evidence}<br>

    </c:forEach>
    <input type="submit" value="Submit">

</form>




</body>
</html>
