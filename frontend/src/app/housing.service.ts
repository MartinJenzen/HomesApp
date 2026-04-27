import { Injectable } from '@angular/core';
import { HousingLocation } from './housing-location';

@Injectable({
  providedIn: 'root'
})
export class HousingService {
  url = 'http://localhost:8080/locations';

  constructor() { }

  async getAllHousingLocations(): Promise<HousingLocation[]> {
    try {
      const response = await fetch(this.url);
    
      if (!response.ok) 
        throw new Error(`Failed to fetch housing locations (${response.status}).`);

      return (await response.json()) as HousingLocation[];
    }
    catch (error) {
      throw new Error('Unable to retrieve housing locations.', { cause: error });
    }
  }

  async getHousingLocationById(id: number): Promise<HousingLocation> {
    try {
      const response = await fetch(`${this.url}/${id}`);
    
      if (!response.ok)
        throw new Error(`Failed to fetch housing location with id: ${id} (${response.status})`);

      return (await response.json()) as HousingLocation;  
    }
    catch (error) {
      throw new Error('Unable to retrieve housing location.', { cause: error });
    }
  }

  submitApplication(firstName: string, lastName: string, email: string): void {
    console.log('Application submitted: ', { firstName, lastName, email });
  }

  async getPhotoUrlById(id: number | null | undefined): Promise<string> {
    try {
      if (id == null)
        return this.getPlaceholderPhotoUrl();

      const response = await fetch(`${this.url}/${id}/photo`);

      if (!response.ok)
        throw new Error(`Failed to fetch photo for housing location with id: ${id} (${response.status})`);

      return `${this.url}/${id}/photo`;
    }
    catch (error) {
      console.error('Unable to retrieve photo URL.', { cause: error });
      return this.getPlaceholderPhotoUrl();
    }
  }

  getPlaceholderPhotoUrl(): string {
    return '/assets/imagePlaceholder.svg';
  }
}