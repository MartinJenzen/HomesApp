import { Component, Input } from '@angular/core';
import { HousingLocation } from '../housing-location';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-housing-location',
  standalone: true,
  imports: [RouterLink],
  template: `
    <!-- TODO: make whole section clickable -->
    <section class="listing">
      <img class="listing-photo" [src]="housingLocation.photo" alt="[Exterior photo of {{ housingLocation.name }}]" />
      <h2 class="listing-heading">{{ housingLocation.name }}</h2>
      <p class="listing-location">{{ housingLocation.city }}, {{ housingLocation.state }}</p>
      <a [routerLink]="['/details', housingLocation.id]">Learn More</a> <!--  housingLocation.id (from the route parameter) is used to create a dynamic link to the details page for each housing location -->
    </section>
  `,
  styleUrl: './housing-location.component.css'
})
export class HousingLocationComponent {
  @Input() housingLocation!: HousingLocation; // Property that is decorated with @Input() to receive data from a parent component
}
