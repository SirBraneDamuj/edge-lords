export interface Deck {
  id: number
  name: string
  master: string
  cards: Record<string, number>
}
