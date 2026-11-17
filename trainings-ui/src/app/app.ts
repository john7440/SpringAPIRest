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
  cart: CartLine[] = [];
  showCart = false;
  error = ''

  constructor(private readonly service: TrainingService) {}

  ngOnInit(): void {
    this.service.findAll().subscribe(data => {
      this.trainings = data;
      data.forEach(t => (this.quantities[t.id!] = 1));
    });
  }

  addToCart(t: Training): void {
    const qty = this.quantities[t.id!] || 1;
    const line = this.cart.find(l => l.training.id === t.id);
    const inCart = line ? line.qty : 0;

    if (inCart + qty > t.quantity){
      this.error = "Stock insuffisant";
      return;
    }
    this.error ='';

    if (line) {
      line.qty += qty;
    } else {
      this.cart.push({ training: t, qty });
    }
  }

  removeFromCart(id: number): void {
    this.cart = this.cart.filter(l => l.training.id !== id);
  }

  get total(): number {
    return this.cart.reduce((sum, l) => sum + l.training.price * l.qty, 0);
  }
}
