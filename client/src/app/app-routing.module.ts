/**
 * Created by Max on 28.03.2017.
 */
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import { LoginComponent } from './login.component';
import { RankingComponent } from './ranking.component';

const routes: Routes = [
  { path: '', redirectTo: '/ranking', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'ranking', component: RankingComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}

