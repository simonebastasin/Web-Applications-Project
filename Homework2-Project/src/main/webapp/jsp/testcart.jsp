<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test cart</title>
</head>
<body>

<div id="response">
    <textarea id="json">
        {
            "cart": [{
                "quantity": 2,
                "alias": "6465661284410"
            }, {
                "quantity": 3,
                "alias": "6465661284414"
            }, {
                "quantity": 4,
                "alias": "6465661284412"
            }]
        }
    </textarea>
    <button onclick="testCart()">Click me</button></div>
<script>
    function testCart() {
        var json = JSON.parse(document.getElementById("json").value);
        json = JSON.stringify(json, undefined, 4);
        document.getElementById("json").value = json;
        var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                document.getElementById("response").innerHTML = xmlhttp.responseText.replace("action=\"\"", "action=\""+ xmlhttp.responseURL +"\"");
            }
        }
        xmlhttp.open("POST", "<c:url value="/buy/cart" />");
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(json);
    }
</script>



</body>
</html>