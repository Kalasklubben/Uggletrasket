<?php
	
	include("inc_connecting.php");
	
$Id=$_POST ['Id'];
$Showtimes=$_POST ['Showtimes'];
	
	$sql = "UPDATE Questions SET Showtimes='{$Showtimes}' WHERE Id='{$Id}'";
	
	
	$resultat = mysql_query($sql);
	
	
	
		
		
		while($Program = mysql_fetch_assoc($resultat)){
			
			
			$out[]=$Program;

		}
		
		print(json_encode($out));
		
	 	
	?>

		