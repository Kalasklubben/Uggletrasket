<?php
	
	include("inc_connecting.php");
	
$Id=$_POST ['Id'];
$NoCorrectAnswer=$_POST ['NoCorrectAnswer'];
	
	$sql = "UPDATE Questions SET NoCorrectAnswer='{$NoCorrectAnswer}' WHERE Id='{$Id}'";
	
	
	$resultat = mysql_query($sql);
	
	
	
		
		
		while($Program = mysql_fetch_assoc($resultat)){
			
			
			$out[]=$Program;

		}
		
		print(json_encode($out));
		
	 	
	?>


