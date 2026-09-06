export type MediaType = 'FILM' | 'SERIE' | 'MANGA' | 'LIVRE' | 'ALBUM';

/**
 * Miroir côté frontend de la hiérarchie Media du catalog-service.
 * Comme le backend renvoie un objet à plat (via l'héritage JPA SINGLE_TABLE
 * + le polymorphisme Jackson), tous les champs spécifiques cohabitent ici
 * dans une seule interface, optionnels selon le type.
 */
export interface Media {
  id?: number;
  type: MediaType;
  titre: string;
  imageUrl?: string;
  annee?: number;
  description?: string;

  // Champs spécifiques FILM
  dureeMinutes?: number;
  realisateur?: string;

  // Champs spécifiques SERIE
  nombreSaisons?: number;
  nombreEpisodes?: number;
  plateforme?: string;
  enCours?: boolean;

  // Champs spécifiques MANGA (auteur partagé avec LIVRE, enCours avec SERIE)
  nombreTomes?: number;

  // Champs spécifiques LIVRE (auteur partagé avec MANGA)
  auteur?: string;
  nombrePages?: number;
  editeur?: string;

  // Champs spécifiques ALBUM
  artiste?: string;
  nombrePistes?: number;
  genreMusical?: string;
}

export const MEDIA_TYPES: MediaType[] = ['FILM', 'SERIE', 'MANGA', 'LIVRE', 'ALBUM'];
