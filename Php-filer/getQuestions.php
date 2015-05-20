<?php
	
	include("inc_connecting.php");
	
	
	$sql = "SELECT * FROM Questions";
	
	
	$resultat = mysql_query($sql);
	
	
	
		
		
		while($Program = mysql_fetch_assoc($resultat)){
			
			
			$out[]=$Program;

		}
		
		print(json_encode($out));
		
	 	
	?>


