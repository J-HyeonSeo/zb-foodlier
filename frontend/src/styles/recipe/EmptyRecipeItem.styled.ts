import styled from 'styled-components'
import { breakpoints, palette } from '../../constants/Styles'

export const Container = styled.button`
  display: flex;
  align-items: center;
  width: 100%;

  ${breakpoints.large} {
    flex-direction: column;
    width: 30%;
    height: 50%;
  }
`

export const Title = styled.span`
  color: ${palette.textSecondary};
  font-size: 1.4rem;
  margin-top: 1rem;
`

export const Image = styled.div`
  width: 30%;
  height: 10rem;
  /* background-color: ${palette.divider}; */
  border: 1px solid ${palette.divider};
  box-shadow: 1px 1px 1px ${palette.shadow};
  margin-right: 1rem;
  border-radius: 1rem;

  ${breakpoints.large} {
    width: 100%;
    height: 25rem;
    margin: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
`
