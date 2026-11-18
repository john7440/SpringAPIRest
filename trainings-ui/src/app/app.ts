import { Component, OnInit, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CartLine, Training, TrainingService } from './training.service';

@Component({ selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  trainings = signal<Training[]>([]);
  cart = signal<CartLine[]>([]);
  showCart = signal(false);
  error = signal('');
  quantities: Record<number, number> = {};

  total = computed(() =>
    this.cart().reduce((sum, l) => sum + l.training.price * l.quantity, 0));

  constructor(private readonly service: TrainingService) {}

  ngOnInit(): void {
    this.refresh();
  }

  private refresh(): void {
    this.service.findAll().subscribe(data => {
      data.forEach(t => (this.quantities[t.id!] = this.quantities[t.id!] || 1));
      this.trainings.set(data);
    });
    this.service.getCart().subscribe(c => this.cart.set(c));
  }

  addToCart(t: Training): void {
    this.service.addToCart(t.id!, this.quantities[t.id!] || 1).subscribe({
      next: () => {
        this.error.set('');
        this.quantities[t.id!] = 1;
        this.refresh();
      },
      error: err => {
        this.error.set(err.error?.message ?? "Erreur lors de l'ajout au panier");
        this.refresh();
      }
    });
  }

  removeFromCart(trainingId: number): void {
    this.service.removeFromCart(trainingId).subscribe(() => this.refresh());
  }
}
