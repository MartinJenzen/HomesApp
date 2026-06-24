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
      <form (submit)="filterResults(filter.value, $event)">
        <input #filter type="text" placeholder="Filter by city or state">
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

  private stateNameToCodeMap = new Map<string, string>([
      ["ALASKA", "AK"],
      ["CALIFORNIA", "CA"],
      ["ILLINOIS", "IL"],
      ["INDIANA", "IN"],
      ["OREGON", "OR"]
    ]);
    
  private stateCodes = new Set<string>(["AK", "CA", "IL", "IN", "OR"]);

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

  // Filtering by city or state
  filterResults(text: string, event: Event): void {
    event.preventDefault(); // Prevents the default form submission behavior, which would cause a page reload
    
    if (!text) {
      this.filteredLocationList = this.housingLocationList;
      return;
    }
    
    const textTrimmed = text.trim();
    const textUpperCase = textTrimmed.toUpperCase();

    // State name
    if (this.stateNameToCodeMap.has(textUpperCase)) {
      const stateCode = this.stateNameToCodeMap.get(textUpperCase);

      this.filteredLocationList = this.housingLocationList.filter(
        housingLocation => housingLocation?.state.toUpperCase() === stateCode?.toUpperCase()
      );
    } 
    // State code
    else if (this.stateCodes.has(textUpperCase)) {
      this.filteredLocationList = this.housingLocationList.filter(
        housingLocation => housingLocation?.state?.toUpperCase() === textUpperCase
      );
    }
    // City
    else {
      this.filteredLocationList = this.housingLocationList.filter(
        housingLocation => housingLocation?.city?.toUpperCase().includes(textUpperCase)
      );
    }
  }
}