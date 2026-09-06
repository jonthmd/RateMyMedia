import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MediaService } from '../../services/media.service';
import { Media, MEDIA_TYPES } from '../../models/media.model';

@Component({
  selector: 'app-media-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './media-form.component.html',
  styleUrl: './media-form.component.css',
})
export class MediaFormComponent {
  private mediaService = inject(MediaService);
  private router = inject(Router);

  readonly types = MEDIA_TYPES;

  media: Media = { type: 'FILM', titre: '' };

  submitting = signal(false);
  error = signal<string | null>(null);

  /**
   * Repart d'un objet propre en changeant de type, pour éviter d'envoyer au
   * backend des champs spécifiques à un type précédemment sélectionné
   * (ex: nombrePages resté rempli après être passé de LIVRE à FILM).
   */
  onTypeChange(): void {
    this.media = { type: this.media.type, titre: this.media.titre, annee: this.media.annee };
  }

  submit(): void {
    this.submitting.set(true);
    this.error.set(null);

    this.mediaService.create(this.media).subscribe({
      next: (created) => {
        this.submitting.set(false);
        this.router.navigate(['/medias', created.id]);
      },
      error: () => {
        this.error.set("Ce média n'a pas pu être ajouté. Vérifie les champs et que catalog-service est démarré.");
        this.submitting.set(false);
      },
    });
  }
}
