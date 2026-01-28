$serviceName = "app"
$containerName = "apiweb_tool"
$imageName = "myapp:1.0"
$dockerComposeFile = "docker-compose_postgresql.yml"

try {
    docker build -t $imageName .
    docker-compose.exe -f $dockerComposeFile up -d
    docker-compose.exe -f $dockerComposeFile attach app
} catch {
    Write-Error "Docker n'est pas installé."
}
