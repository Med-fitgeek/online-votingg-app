export interface VoteRequest {
  token: string;     // UUID envoyé au votant
  optionId: number;  // ID de l’option choisie
}
