<?php
	
	include("inc_connecting.php");

	$QuizId=$_GET ['QuizId'];
	
	
	$sql = "SELECT * FROM Questions WHERE QuizId = '{$QuizId}'";
	
	
	$resultat = mysql_query($sql);
	
	
	
		
		
		while($Program = mysql_fetch_assoc($resultat)){
			
			
			$out[]=$Program;

		}
		
		print(json_encode($out));
		
	 	
	?>


