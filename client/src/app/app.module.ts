import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login.component';
import { RankingComponent } from './ranking.component';
import { CheckLoginService } from './check-login.service';
import { Globals } from './globals';
import { AdminComponent } from './admin/admin.component';
import { SpielerDetailComponent } from './admin/spieler-detail/spieler-detail.component';
import {AuthGuard} from "./admin/auth-guard.service";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RankingComponent,
    AdminComponent,
    SpielerDetailComponent
  ],
  imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      AppRoutingModule
  ],
  providers: [
      CheckLoginService,
      Globals,
      AuthGuard
  ],
  bootstrap: [
      AppComponent
  ]
})
export class AppModule { }
