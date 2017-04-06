import { Component, Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  // template: `
  //   <router-outlet></router-outlet>
  // `,
  templateUrl: './app.component.html'
})
@Injectable()
export class AppComponent {
  title = 'SG Rüsselbach';
  constructor (private router: Router)
  {}

}
