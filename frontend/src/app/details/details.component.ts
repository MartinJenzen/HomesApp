import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HousingService } from '../housing.service';
import { HousingLocation } from '../housing-location';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-details',
  imports: [ReactiveFormsModule, MatProgressSpinnerModule],
  standalone: true,
  template: `

    @if (isLoading) {
      <section class="spinner-container">
        <mat-spinner mode="indeterminate" aria-label="Loading housing location details..."></mat-spinner>
      </section>
    }
    @else {
      <article>
        <img 
          class="listing-photo" 
          [src]="photoUrl"
          alt="Exterior photo of {{ housingLocation?.name }}"
        >

        <section class="listing-description">
          <h2 class="listing-heading">{{ housingLocation?.name }}</h2>
          <p class="listing-location">{{ housingLocation?.city }}, {{ housingLocation?.state }}</p>
        </section>

        <section class="listing-features">
          <h2 class="section-heading">Features:</h2>
          <ul>
            <li>Units Available: {{ housingLocation?.availableUnits }}</li>
            <li>WiFi: {{ housingLocation?.wifi ? 'Yes' : 'No' }}</li>
            <li>Laundry: {{ housingLocation?.laundry ? 'Yes' : 'No' }}</li>
          </ul>
        </section>

        <section class="listing-apply">
          <h2 class="section-heading">Interested in this location?</h2>

          <form [formGroup]="applyForm" (submit)="submitApplication()">
            <label for="first-name">First Name:</label> <!-- 'for' attribute is used to associate the label with the corresponding input field -->
            <input id="first-name" type="text" formControlName="firstName" placeholder="First Name"> <!-- 'id' attribute is used to uniquely identify the input field, and 'formControlName' is used to bind the input field to the corresponding form control in the component -->

            <label for="last-name">Last Name:</label>
            <input id="last-name" type="text" formControlName="lastName" placeholder="Last Name">

            <label for="email">Email:</label>
            <input id="email" type="email" formControlName="email" placeholder="Email">

            <button class="primaryButton" type="submit" [disabled]="applyForm.invalid">Apply Now</button>
          </form>
        </section>
      </article>
    }
  `,
  styleUrl: './details.component.css'
})
export class DetailsComponent {
  route: ActivatedRoute = inject(ActivatedRoute); // ActivatedRoute service is injected into the component to access route parameters
  housingService: HousingService = inject(HousingService);
  housingLocation: HousingLocation | undefined;
  photoUrl: string | undefined;
  isLoading: boolean = true;
  
  // Reactive form: FormGroup is used to manage the state of the form, and FormControl is used to define individual form controls with their respective validators
  applyForm = new FormGroup({ 
    firstName: new FormControl('', { validators: [Validators.required] }),
    lastName: new FormControl('', { validators: [Validators.required] }),
    email: new FormControl('', { validators: [Validators.required, Validators.email] })
  });

  constructor() {
    const housingLocationId = Number(this.route.snapshot.params['id']); // Route parameter 'id' is accessed using the snapshot of the ActivatedRoute
    this.loadHousingLocation(housingLocationId);
  }

  async loadHousingLocation(housingLocationId: number): Promise<void> {
    try {
      this.housingLocation = await this.housingService.getHousingLocationById(housingLocationId);
      this.photoUrl = await this.housingService.getPhotoUrlById(this.housingLocation?.id);
    }
    catch (error) {
      console.error(`Error loading housing location: `, error);
      // TODO: Display an error message to the user, e.g., using a toast notification or an alert box
    }
    finally {
      this.isLoading = false;
    }
  }

  submitApplication() {
    this.housingService.submitApplication(
      this.applyForm.value.firstName ?? '',
      this.applyForm.value.lastName ?? '',
      this.applyForm.value.email ?? ''
    );
  }
}