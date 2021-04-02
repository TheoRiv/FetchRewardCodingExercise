<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<title>Payer information</title>
<body>
<div>Correct page </div>

<div>List here: 
<textarea id="listPayer" rows="5" cols="40">${listPayerBalance}</textarea>
</div>
<div style="margin-bottom: 2.5em; margin-top: 2.5em">
<form class= "myForm" method="post" enctype="multipart/form-data" action="/addtransaction">
	<label style= "float: left;"> Payer
		<select id="payerName" name="payerName" style="width:40em; margin-left:5em;">
			<option value="" selected="selected">Select a Payer to do a transaction</option>
			<c:forEach var="listPayer" items="${listPayerName}">
				<c:choose>
					<c:when test="${payerName==listPayer}">
						<option value="${listPayer}" selected>${listPayer}</option>
						<br />
					</c:when>
					<c:otherwise>
						<option value="${listPayer}">${listPayer}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
	</label>
	<label for="points">Enter the number of points for the transaction</label>
	<input type="number" id="transactionPoints" name="transactionPoints"> 
	<input type="submit" value="Add Transaction" onclick="form.action='/addtransaction';">
</form>
</div>

<div style="margin-bottom: 2.5em; margin-top: 2.5em">
<form class= "myForm" method="post" action="spendpoints">
	<label for="spendPointsValue">Enter the number of points to spend amongst the payers</label>
	
	<input type="number" id="spendPointsValue" name="spendPointsValue"> 
	<input type="submit" value="Spend Points" onclick="form.action='spendpoints';">
</form>
</div>

</body>
</html> 