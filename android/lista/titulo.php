<?php
$servername = "localhost";
$username = "root";
$password = "1234";
$dbname = "ramirezboxingclub";
$con=0;
$im="";
date_default_timezone_set('America/Mexico_City');
$day=date('l');
// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$vacio = FALSE;

if($day=="Monday"){
$sql = "select  rutinas.Clave_Ejercicio from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where rutinas.Cliente_Id_Cliente =".$_GET["ID"]." AND rutinas.Dia = 'Lunes'";
}else
if($day=="Tuesday"){
$sql = "select  rutinas.Clave_Ejercicio from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where rutinas.Cliente_Id_Cliente =".$_GET["ID"]." AND rutinas.Dia = 'Martes'";
}else
if($day=="Wednesday"){
$sql = "select  rutinas.Clave_Ejercicio from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where rutinas.Cliente_Id_Cliente =".$_GET["ID"]." AND rutinas.Dia = 'Miercoles'";
}else
if($day=="Thursday"){
$sql = "select  rutinas.Clave_Ejercicio from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where rutinas.Cliente_Id_Cliente =".$_GET["ID"]." AND rutinas.Dia = 'Jueves'";
}else
if($day=="Friday"){
$sql = "select  rutinas.Clave_Ejercicio from ejercicio INNER JOIN rutinas ON ejercicio.Clave_Ejercicio = rutinas.Clave_Ejercicio where rutinas.Cliente_Id_Cliente =".$_GET["ID"]." AND rutinas.Dia = 'Viernes'";
}else{
$sql = "select  nomre from logo";
$vacio = True;
}


$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
 if($vacio==True){

 $im=   "[{\"imagen\": "."\"".$row["nomre"]."\"}]";
       echo $im;
 }

      if($vacio==False){
    	if(mysqli_num_rows($result)==1){
    		 $im=   "[{\"imagen\": "."\"".$row["Clave_Ejercicio"]."\"}]";
       echo $im;
    	}
    	if ($con==0&&$con<(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)!=1) {
        $im=   "[{\"imagen\": "."\"".$row["Clave_Ejercicio"]."\"},";
       echo $im;
       }
    	if ($con==(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)!=1) {
        $im=   "{\"imagen\": "."\"".$row["Clave_Ejercicio"]."\"}]";
       echo $im;
       }if($con>0&&$con<(mysqli_num_rows($result)-1)&&mysqli_num_rows($result)>1){  $im=   "{\"imagen\": "."\"".$row["Clave_Ejercicio"]."\"}, ";
       echo $im;}
       $con++;}
    }
   
} else {
    echo "0 results";
}

mysqli_close($conn);
?>
