import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Training, TrainingService } from './training.service';

interface CartLine {
  training: Training;
  qty: number;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  trainings: Training[] = [];
  quantities: Record<number, number> = {};

  constructor(private readonly service: TrainingService) {}

  ngOnInit(): void {
    this.service.findAll().subscribe(data => {
      this.trainings = data;
      data.forEach(t => (this.quantities[t.id!] = 1));
    });
  }
}
