<?php
$servername = "localhost";
$username = "root";
$password = "1234";
$dbname = "ramirezboxingclub";
$con=0;
$im="";

$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "select Id_Cliente from cliente where Id_Cliente =".$_GET["ID"]."";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
    	if(mysqli_num_rows($result)==1){
    		 $im=   "[{\"imagen\": "."\"".$row["Id_Cliente"]."\"}]";
       echo $im;
    	}
    	if ($con==0&&$con<(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)!=1) {
        $im=   "[{\"imagen\": "."\"".$row["Id_Cliente"]."\"},";
       echo $im;
       }
    	if ($con==(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)!=1) {
        $im=   "{\"imagen\": "."\"".$row["Id_Cliente"]."\"}]";
       echo $im;
       }if($con>0&&$con<(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)>1){  $im=   "{\"imagen\": "."\"".$row["Id_Cliente"]."\"}, ";
       echo $im;}
       $con++;
    }
   
} else {
    echo "0 results";
}

mysqli_close($conn);
?>
