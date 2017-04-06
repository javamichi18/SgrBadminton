import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {RouterModule, Routes} from "@angular/router";

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RankingComponent } from './ranking.component';
import { CheckLoginService } from './check-login.service';
import { Globals } from './globals';
import { AdminComponent } from './admin/admin.component';
import { SpielerDetailComponent } from './admin/spieler-detail/spieler-detail.component';
import {AuthGuard} from "./admin/auth-guard.service";
import {SpielerService} from "./spieler.service";
import {ForderungenService} from "./forderungen.service";
import {ForderungenComponent} from "./forderungen.component";



@NgModule({
  declarations: [
    AppComponent,
    RankingComponent,
    ForderungenComponent,
    AdminComponent,
    SpielerDetailComponent
  ],
  imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      AppRoutingModule,
      RouterModule,
  ],
  providers: [
      CheckLoginService,
      Globals,
      AuthGuard,
      SpielerService,
      ForderungenService
  ],
  bootstrap: [
      AppComponent
  ]
})
export class AppModule { }
