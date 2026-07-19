# Deploy no Render.com

## Pré-requisitos

1. Conta no [Render.com](https://render.com)
2. Repositório GitHub com este código
3. Um banco de dados MySQL (você pode usar o Render MySQL ou outro serviço)

## Instruções de Deploy

### 1. Conectar Repositório GitHub

1. Vá para [Render Dashboard](https://dashboard.render.com)
2. Clique em "New +" → "Web Service"
3. Selecione "Deploy from a Git repository"
4. Conecte sua conta GitHub e autorize o Render.com
5. Selecione o repositório

### 2. Configurar o Serviço Web

- **Name**: `demo-api` (ou o nome desejado)
- **Runtime**: Docker
- **Region**: Ohio (ou a mais próxima)
- **Build Command**: Deixar em branco (Render detecta automaticamente)
- **Start Command**: Deixar em branco
- **Plan**: Starter ou superior

### 3. Configurar Variáveis de Ambiente

Clique em "Advanced" e adicione as seguintes variáveis:

```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:mysql://seu-host:porta/seu-banco?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DATABASE_USER=seu_usuario
DATABASE_PASSWORD=sua_senha
JWT_SECRET=sua-chave-secreta-com-mais-de-32-caracteres
```

### 4. Configurar Health Check

- **Health Check Path**: `/actuator/health`
- **Health Check Protocol**: HTTP
- **Check Interval**: 30 segundos

### 5. Deploy

Clique em "Create Web Service" e o Render.com começará o deploy.

## Monitoramento

- Acompanhe os logs em tempo real no Render Dashboard
- Use `/actuator/health` para verificar o status

## Otimizações Implementadas

✅ **Multi-stage Docker Build**: Reduz o tamanho da imagem
✅ **Alpine Linux**: Base mínima para menor footprint
✅ **G1GC Garbage Collector**: Otimizado para ambientes cloud
✅ **Connection Pool**: Hikari CP configurado para 10 conexões máx
✅ **Compressão HTTP**: Reduz tempo de transferência
✅ **Usuário não-root**: Melhor segurança
✅ **Health Check**: Permite auto-recovery do Render
✅ **Porta Dinâmica**: Compatível com `${PORT}`
✅ **Profile de Produção**: Logging mínimo, segurança máxima

## Troubleshooting

### Erro de Conexão com Banco de Dados
- Verifique se as credenciais estão corretas
- Confirme se o banco permite conexões remotas
- Tente adicionar `&allowPublicKeyRetrieval=true` na URL

### Aplicação lenta
- Aumente o plan de Render (standard, pro)
- Verifique se o banco de dados está em boa saúde
- Monitore logs em time real

### Health Check falha
- Garanta que o Actuator esteja habilitado (está por padrão)
- Verifique a porta (deve ser dinâmica via `${PORT}`)
