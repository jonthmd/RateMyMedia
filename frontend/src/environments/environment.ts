/**
 * Les URLs des APIs sont calculées à partir de l'hôte qui a servi la page
 * (window.location.hostname), plutôt que codées en dur sur "localhost".
 * Ça permet au même build de fonctionner :
 * - en desktop : hostname = "localhost" → http://localhost:8081/...
 * - dans l'émulateur Android : hostname = "10.0.2.2" (alias vers la machine
 *   hôte) → http://10.0.2.2:8081/..., qui atteint bien catalog-service
 *   tournant sur la machine hôte via docker compose.
 */
function apiHost(): string {
  return typeof window !== 'undefined' ? window.location.hostname : 'localhost';
}

export const environment = {
  production: false,
  get catalogApiUrl(): string {
    return `http://${apiHost()}:8081/api/medias`;
  },
  get reviewApiUrl(): string {
    return `http://${apiHost()}:8082/api/reviews`;
  },
};
