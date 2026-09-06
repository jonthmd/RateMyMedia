import { Routes } from '@angular/router';
import { MediaListComponent } from './components/media-list/media-list.component';
import { MediaDetailComponent } from './components/media-detail/media-detail.component';
import { MediaFormComponent } from './components/media-form/media-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'medias', pathMatch: 'full' },
  { path: 'medias', component: MediaListComponent },
  { path: 'medias/nouveau', component: MediaFormComponent },
  { path: 'medias/:id', component: MediaDetailComponent },
];
