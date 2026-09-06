import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MediaService } from '../../services/media.service';
import { Media, MediaType, MEDIA_TYPES } from '../../models/media.model';

@Component({
  selector: 'app-media-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './media-list.component.html',
  styleUrl: './media-list.component.css',
})
export class MediaListComponent implements OnInit {
  private mediaService = inject(MediaService);

  readonly types = MEDIA_TYPES;

  medias = signal<Media[]>([]);
  selectedType = signal<MediaType | ''>('');
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadMedias();
  }

  onFilterChange(value: string): void {
    this.selectedType.set(value as MediaType | '');
    this.loadMedias();
  }

  private loadMedias(): void {
    this.loading.set(true);
    this.error.set(null);

    this.mediaService.getAll(this.selectedType()).subscribe({
      next: (medias) => {
        this.medias.set(medias);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Le catalogue ne répond pas. Vérifie que catalog-service est démarré.');
        this.loading.set(false);
      },
    });
  }
}
