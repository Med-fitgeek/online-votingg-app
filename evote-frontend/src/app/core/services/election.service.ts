import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ElectionRequest } from '../../shared/models/election-request.model';
import { ElectionResponse } from '../../shared/models/election-response.model';
import { VoterToken } from '../../shared/models/voter-token.model';
import { VoteRequest } from '../../shared/models/vote-request.model';

@Injectable({
  providedIn: 'root'
})
export class ElectionService {
  private readonly API_URL = 'http://localhost:8080/api/elections'; // adapte si besoin

  constructor(private http: HttpClient) {}

  createElection(request: ElectionRequest): Observable<VoterToken[]> {
    return this.http.post<VoterToken[]>(`${this.API_URL}/create`, request);
  }

  getElections(): Observable<ElectionResponse[]> {
    return this.http.get<ElectionResponse[]>(`${this.API_URL}/elections`);
  }

  getElection(id: number): Observable<ElectionResponse> {
    return this.http.get<ElectionResponse>(`${this.API_URL}/election/${id}`);
  }

  updateElection(id: number, request: ElectionRequest): Observable<string> {
    return this.http.put<string>(`${this.API_URL}/${id}/update`, request);
  }

  deleteElection(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}/delete`);
  }

  getResults(id: number): Observable<any> {
    return this.http.get(`${this.API_URL}/results/${id}`);
  }

  vote(request : VoteRequest) : Observable<any> {
    return this.http.post(`${this.API_URL}/vote`, request);
  }
}
