import { Component, inject } from '@angular/core';
import { HousingLocationComponent } from '../housing-location/housing-location.component';
import { HousingLocation } from '../housing-location';
import { HousingService } from '../housing.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-home',
  standalone: true, // Indicates that this component is self-contained and does not require an Angular module to be declared in
  imports: [HousingLocationComponent, MatProgressSpinnerModule],
  template: `
    <section>
      <!-- <form (submit)="$event.preventDefault(); filterResults(filter.value)"> -->
      <form (submit)="filterResults(filter.value, $event)">
        <input #filter type="text" placeholder="Filter by city...">
        <button class="primaryButton" type="submit">Search</button>
      </form>
    </section>

    @if (isLoading) {
      <section class="spinner-container">
        <mat-spinner mode="indeterminate" aria-label="Loading housing locations..."></mat-spinner>
      </section>
    }
    @else {
      <section class="results">
        @for (housingLocation of filteredLocationList; track housingLocation.id) {
          <app-housing-location
            [housingLocation]="housingLocation"
          ></app-housing-location>
        }
      </section>
    }
  `,
  styleUrl: './home.component.css'
})
export class HomeComponent {
  housingLocationList: HousingLocation[] = [];
  housingService: HousingService = inject(HousingService);
  filteredLocationList: HousingLocation[] = [];
  isLoading: boolean = true;

  constructor() {
    this.loadHousingLocations();
  }

  async loadHousingLocations(): Promise<void> {
    try {
      this.housingLocationList = await this.housingService.getAllHousingLocations();
      this.filteredLocationList = this.housingLocationList;
    }
    catch (error) {
      console.error('Error loading housing locations: ', error);
      // TODO: Display an error message to the user, e.g., using a toast notification or an alert box
    }
    finally {
      this.isLoading = false;
    }
  }

  // TODO: enable filtering by state, etc.
  filterResults(text: string, event: Event): void {
    event.preventDefault(); // Prevents the default form submission behavior, which would cause a page reload
    
    // TODO: Implement basic input validation, e.g., check for special characters that might cause issues with filtering or display a warning if the input is too long

    if (!text)
      this.filteredLocationList = this.housingLocationList;
    else {
      this.filteredLocationList = this.housingLocationList.filter(
        housingLocation => housingLocation?.city?.toLowerCase().includes(text.toLowerCase())
      );
    }
  }
}