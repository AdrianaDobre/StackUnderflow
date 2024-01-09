import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';

export const routes: Routes = [
    {path: 'login', component: LoginFormComponent, pathMatch: 'full'}
];
