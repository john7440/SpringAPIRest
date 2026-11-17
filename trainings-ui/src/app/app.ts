import { Component, OnInit, computed, signal} from '@angular/core';
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
  trainings = signal<Training[]>([]);
  quantities: Record<number, number> = {};
  cart = signal<CartLine[]>([]);
  showCart = signal(false);
  error = signal('');

  total = computed(() =>
    this.cart().reduce((sum, l) => sum + l.training.price * l.qty, 0)
  );

  constructor(private readonly service: TrainingService) {}

  ngOnInit(): void {
    this.service.findAll().subscribe(data => {
      data.forEach(t => (this.quantities[t.id!] = 1));
      this.trainings.set(data);
    });
  }

  addToCart(t: Training): void {
    const qty = this.quantities[t.id!] || 1;
    const line = this.cart().find(l => l.training.id === t.id);
    const inCart = line ? line.qty : 0;

    if (inCart + qty > t.quantity) {
      this.error.set(`Stock insuffisant pour ${t.name}`);
      return;
    }
    this.error.set('');

    if (line) {
      this.cart.update(c => c.map(l => (l.training.id === t.id ? { ...l, qty: l.qty + qty } : l)));
    } else {
      this.cart.update(c => [...c, { training: t, qty }]);
    }
  }

  removeFromCart(id: number): void {
    this.cart.update(c => c.filter(l => l.training.id !== id));
  }
}
