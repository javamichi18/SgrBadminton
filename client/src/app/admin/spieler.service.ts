import {Injectable} from "@angular/core";
import {Spieler} from "../spieler";
/*import { SpielerService } from "../mock-spieler";*/


@Injectable()
export class SpielerService {

    public getTitle() {
        return 'Hi from my-service.service.ts'
    }

    public getSpielerPlain(): Spieler[] {
        let spieler: Spieler[] = [];
        /*this.spielerservice.Spielerholen().subscribe(
            sp => spieler = sp
        );*/
        return null;
    }

    public  getSpieler(): Promise<Spieler[]> {

        return Promise.resolve(this.getSpielerPlain());
    }

    public getSpielerDetails(id: number): Promise<Spieler> {

        let s = this.getSpielerPlain()[0]; //find(s => s.id === id);
        return Promise.resolve(s);

        // return this.getSpieler()
        //     .then(spieler => );
    }

    public getSpielerDetailsPlain(id: number): Spieler {

        return this.getSpielerPlain().find(s => s.id === id);
    }

    constructor(/*private spielerservice: SpielerService*/) {
    }

}
