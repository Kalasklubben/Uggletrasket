

<?php
	
	include("inc_connecting.php");
	
	$Id=$_POST ['Id'];

	$sql = "SELECT * FROM Questions WHERE Id='{$Id}'”;
	
	
	$resultat = mysql_query($sql);
	
	
	
		
		
		while($Program = mysql_fetch_assoc($resultat)){
			
			
			$out[]=$Program;

		}
		
		print(json_encode($out));
		
	 	
	?>


