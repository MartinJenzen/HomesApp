import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterLink, RouterOutlet],
  // templateUrl: './app.component.html', // Welcome to Angular page
  standalone: true,
  template: `
    <main>
      <a [routerLink]="['/']">
        <header class="brand-name">
          <img class="brand-logo" src="assets/logo.svg" alt="[logo]" aria-hidden="true" />
        </header>
      </a>

      <section class="content">
        <!-- Placeholder that Angular dynamically fills based on the current router state. 
         It serves as the insertion point for the components that are rendered when 
         navigating to different routes in the application. 
        -->
        <router-outlet />
      </section>
    </main>
  `,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'homes'; // Used to be homes-app
}
