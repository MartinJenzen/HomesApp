import { Component, inject, Input } from '@angular/core';
import { HousingLocation } from '../housing-location';
import { RouterLink } from '@angular/router';
import { HousingService } from '../housing.service';

@Component({
  selector: 'app-housing-location',
  standalone: true,
  imports: [RouterLink],
  template: `
    <a class="listing-link" [routerLink]="['/details', housingLocation.id]">
      <section class="listing">
        <img class="listing-photo" [src]="photoUrl" alt="Exterior photo of {{ housingLocation.name }}" />
        <h2 class="listing-heading">{{ housingLocation.name }}</h2>
        
        <div class="listing-meta">
          <div class="listing-location">
            <img class="location-pin" src="/assets/location-pin.svg" alt="" aria-hidden="true" />
            <p class="listing-location">{{ housingLocation.city }}, {{ housingLocation.state }}</p>
          </div>
          <span class="learn-more">Learn More</span>
        </div>
        
      </section>
    </a>
  `,
  styleUrl: './housing-location.component.css'
})
export class HousingLocationComponent {
  @Input() housingLocation!: HousingLocation; // Property that is decorated with @Input() to receive data from a parent component
  housingService: HousingService = inject(HousingService);
  photoUrl: string | undefined;

  // Runs once automatially after initial input binding
  ngOnInit() {
    this.loadPhotoUrl();
  }

  async loadPhotoUrl() {
    try {
      this.photoUrl = await this.housingService.getPhotoUrlById(this.housingLocation.id);
    }
    catch (error) {
      console.error('Error loading photo URL:', error);
    }
  }
}
