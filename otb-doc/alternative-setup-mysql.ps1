docker pull mysql:8.3.0

$containerName = "otb-container"
$mysqlRootPassword = "1111"
$mysqlDatabase = "online_tickets_booking"
$mysqlUser = "user"
$mysqlPassword = "2222"

docker run -d `
  --name $containerName `
  -e MYSQL_ROOT_PASSWORD=$mysqlRootPassword `
  -e MYSQL_DATABASE=$mysqlDatabase `
  -e MYSQL_USER=$mysqlUser `
  -e MYSQL_PASSWORD=$mysqlPassword `
  -p 3306:3306 `
  mysql:8.3.0

Start-Sleep -Seconds 30

$commands = @(
    "CREATE USER '$mysqlUser'@'%' IDENTIFIED BY '$mysqlPassword';",
    "GRANT ALL PRIVILEGES ON $mysqlDatabase.* TO '$mysqlUser'@'%';",
    "FLUSH PRIVILEGES;"
)


foreach ($command in $commands) {
    docker exec -i $containerName mysql -uroot -p$mysqlRootPassword -e $command
}