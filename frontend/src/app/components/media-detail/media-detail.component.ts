import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MediaService } from '../../services/media.service';
import { ReviewService } from '../../services/review.service';
import { Media } from '../../models/media.model';
import { Review } from '../../models/review.model';

@Component({
  selector: 'app-media-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './media-detail.component.html',
  styleUrl: './media-detail.component.css',
})
export class MediaDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private mediaService = inject(MediaService);
  private reviewService = inject(ReviewService);

  media = signal<Media | null>(null);
  reviews = signal<Review[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  newNote = 5;
  newCommentaire = '';
  submitting = signal(false);
  submitError = signal<string | null>(null);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadMedia(id);
    this.loadReviews(id);
  }

  submitReview(): void {
    const media = this.media();
    if (!media?.id) return;

    this.submitting.set(true);
    this.submitError.set(null);

    this.reviewService
      .create({
        mediaId: media.id,
        note: this.newNote,
        commentaire: this.newCommentaire || undefined,
      })
      .subscribe({
        next: () => {
          this.newCommentaire = '';
          this.newNote = 5;
          this.submitting.set(false);
          this.loadReviews(media.id!);
        },
        error: () => {
          this.submitError.set("L'avis n'a pas pu être enregistré. Réessaie dans un instant.");
          this.submitting.set(false);
        },
      });
  }

  get averageNote(): number | null {
    const reviews = this.reviews();
    if (reviews.length === 0) return null;
    const sum = reviews.reduce((acc, r) => acc + r.note, 0);
    return Math.round((sum / reviews.length) * 10) / 10;
  }

  private loadMedia(id: number): void {
    this.mediaService.getById(id).subscribe({
      next: (media) => {
        this.media.set(media);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Ce média est introuvable.');
        this.loading.set(false);
      },
    });
  }

  private loadReviews(id: number): void {
    this.reviewService.getAll(id).subscribe({
      next: (reviews) => this.reviews.set(reviews),
    });
  }
}
