import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../models/review.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ReviewService {
  private http = inject(HttpClient);
  private readonly apiUrl = environment.reviewApiUrl;

  getAll(mediaId?: number): Observable<Review[]> {
    const url = mediaId ? `${this.apiUrl}?mediaId=${mediaId}` : this.apiUrl;
    return this.http.get<Review[]>(url);
  }

  create(review: Review): Observable<Review> {
    return this.http.post<Review>(this.apiUrl, review);
  }
}
