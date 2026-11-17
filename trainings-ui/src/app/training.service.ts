import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Training {
  id?: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
}

@Injectable({ providedIn: 'root' })
export class TrainingService {
  private readonly url = 'http://localhost:8080/api/trainings';

  constructor(private readonly http: HttpClient) {}

  findAll(): Observable<Training[]> {
    return this.http.get<Training[]>(this.url);
  }

  create(t: Training): Observable<Training> {
    return this.http.post<Training>(this.url, t);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.url}/${id}`);
  }
}
