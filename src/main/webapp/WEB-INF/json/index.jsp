<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Epam</title>
	<link rel="stylesheet" href="../resources/styles/styles.css">
</head>

<body>

    <div>
        <form class="viewTypeClass">
            <p>
                Select view type:<br />
                <input class="radioClass" type="radio" name="viewType" value="cities" checked='checked'>Cities
                <input class="radioClass" type="radio" name="viewType" value="global">Global
            </p>
        </form>
    </div>
	
	<div id="container">
		
		<p id="choices"></p>
		<div id="placeholder"></div>
		<div id="legend"></div>
		 
	</div>

</body>

<script type="text/javascript" src="../resources/js/flot/jquery.js"></script>
<script type="text/javascript" src="../resources/js/flot/jquery.flot.js"></script>
<script type="text/javascript" src="../resources/js/parsing_new.js"></script>

</html>