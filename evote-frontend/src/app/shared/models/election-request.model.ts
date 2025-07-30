export interface ElectionRequest {
  title: string;
  description: string;
  startDate: string;  // Format ISO (ex: '2025-08-01T09:00:00')
  endDate: string;
  options: string[]; // libellés des choix de vote
  voters: string[];  // noms ou identifiants des votants
}
