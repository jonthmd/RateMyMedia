import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Media, MediaType } from '../models/media.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class MediaService {
  private http = inject(HttpClient);
  private readonly apiUrl = environment.catalogApiUrl;

  getAll(type?: MediaType | ''): Observable<Media[]> {
    const url = type ? `${this.apiUrl}?type=${type}` : this.apiUrl;
    return this.http.get<Media[]>(url);
  }

  getById(id: number): Observable<Media> {
    return this.http.get<Media>(`${this.apiUrl}/${id}`);
  }

  create(media: Media): Observable<Media> {
    return this.http.post<Media>(this.apiUrl, media);
  }
}
