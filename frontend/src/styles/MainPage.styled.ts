import styled from 'styled-components'
import { breakpoints } from '../constants/Styles'

export const SlickContainer = styled.section`
  margin-top: 3rem;
`

export const Tit = styled.p`
  font-size: 2.5rem;
  font-weight: 800;
  padding: 0 2%;

  ${breakpoints.large} {
    font-size: 3rem;
  }
`

export const WebSearchWrapper = styled.div`
  display: none;

  ${breakpoints.large} {
    display: block;
  }
`

export const WebSearchContainer = styled.div`
  padding: 0 5%;
`

export const RecipeContainer = styled.section`
  margin-bottom: 5rem;

  ${breakpoints.large} {
    margin-bottom: 10rem;
  }
`
export const RecipeTit = styled.section`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-right: 1rem;
  `
export const RecipeList = styled.section`
  flex-direction: column;
  display: flex;
  padding: 0 1rem;
  margin-top: 2rem;

  ${breakpoints.large} {
    flex-direction: row;
    padding: 0 0.5rem;
  }
`
export const ChefContainer = styled.section`
  padding-bottom: 10rem;
`
export const ChefTit = styled.section``
export const ChefList = styled.section`
  display: flex;
  margin: 2rem 1rem 0;
`
