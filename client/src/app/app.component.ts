import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h3>{{title}}</h3>
    <p>Badminton</p>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'SG Rüsselbach';
}
