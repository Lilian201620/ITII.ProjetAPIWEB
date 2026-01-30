$serviceName = "app"
$dockerComposeFile = "docker-compose_postgresql.yml"

try {
    docker-compose.exe -f $dockerComposeFile up -d
    docker-compose.exe -f $dockerComposeFile attach $serviceName
} catch {
    Write-Error "Docker n'est pas installé."
}
