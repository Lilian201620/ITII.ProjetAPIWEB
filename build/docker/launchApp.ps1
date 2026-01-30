$serviceName = "app"
$containerName = "lilian2016/apiwebtool:latest"
$dockerComposeFile = "docker-compose_postgresql.yml"

try {
    docker.exe pull $containerName
    docker-compose.exe -f $dockerComposeFile up -d
    docker-compose.exe -f $dockerComposeFile attach $serviceName
} catch {
    Write-Error "Docker n'est pas installé."
}
