import { Component, OnInit } from '@angular/core';
import { ElectionService } from '../../core/services/election.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  stats = {
    elections: 0,
    totalVoters: 0,
    votesCast: 0
  };

  constructor(private electionService: ElectionService) {}

  ngOnInit(): void {
    this.electionService.getElections().subscribe({
      next: (elections) => {
        this.stats.elections = elections.length;

        for (let election of elections) {
          this.stats.totalVoters += election.voterCount ?? 0;
          this.stats.votesCast += election.votesCast ?? 0;
        }
      },
      error: (err) => {
        console.error('Erreur lors du chargement des élections', err);
      }
    });
  }
}
