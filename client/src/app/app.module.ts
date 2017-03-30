import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login.component';
import { RankingComponent } from './ranking.component';
import { CheckLoginService } from './check-login.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RankingComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    CheckLoginService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
