import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DetailsComponent } from './details/details.component';

export const routes: Routes = [
    {
        path: '', // Default route (home page)
        component: HomeComponent,
        title: 'Home Page'
    },
    {
        path: 'details/:id', // Details page route
        component: DetailsComponent,
        title: 'Details Page'
    }
];
