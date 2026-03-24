$dockerComposeFile = "docker-compose_postgresql.yml"

try {
    docker-compose.exe -f $dockerComposeFile down -d
} catch {
    Write-Error "Docker n'est pas installé. Ou application non lancée"
}