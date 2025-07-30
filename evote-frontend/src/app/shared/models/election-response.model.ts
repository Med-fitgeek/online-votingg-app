export interface ElectionResponse {
  id: number;
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  options: string[];
  voterCount?: number; // total de tokens générés
  votesCast?: number;  // nombre de tokens utilisés  // labels uniquement (pas d’IDs ici)
}
